package com.ehhthan.glowwiki.file;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;

public class DirectoryCopyFileVisitor {
    public static DirectoryCopyFileVisitor INSTANCE = new DirectoryCopyFileVisitor();

    private DirectoryCopyFileVisitor() {}

    public void copy(String source, final Path target) throws URISyntaxException, IOException {
        URI resource = getClass().getResource("").toURI();
        FileSystem fileSystem = FileSystems.newFileSystem(
            resource,
            Collections.<String, String>emptyMap()
        );


        final Path jarPath = fileSystem.getPath(source);
        Files.walkFileTree(jarPath, new SimpleFileVisitor<>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path currentTarget = target.resolve(jarPath.relativize(dir).toString());
                Files.createDirectories(currentTarget);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path resolve = target.resolve(jarPath.relativize(file).toString());
                if (!Files.exists(resolve))
                    Files.copy(file, resolve);
                return FileVisitResult.CONTINUE;
            }

        });
    }
}
