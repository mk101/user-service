package kolesov.maksim.mapping.user.repository;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.*;
import kolesov.maksim.mapping.user.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MinioRepository {

    private static final int PART_SIZE = 10485760;
    private static final String ROOT = "/";

    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucket;

    public void upload(String name, byte[] data) throws ServiceException {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(ROOT + name)
                    .stream(new ByteArrayInputStream(data), -1, PART_SIZE)
            .build());
        } catch (ErrorResponseException | XmlParserException | ServerException | NoSuchAlgorithmException |
                 IOException | InvalidResponseException | InvalidKeyException | InternalException |
                 InsufficientDataException e) {
            throw new ServiceException("Failed to upload file", e);
        }
    }

    public byte[] get(String name) throws ServiceException {
        try {
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(ROOT + name)
            .build()).readAllBytes();
        } catch (ErrorResponseException | XmlParserException | ServerException | NoSuchAlgorithmException |
                 IOException | InvalidResponseException | InvalidKeyException | InternalException |
                 InsufficientDataException e) {
            throw new ServiceException("Failed to load file", e);
        }
    }

    public void delete(String name) throws ServiceException {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucket)
                    .object(ROOT + name)
            .build());
        } catch (ErrorResponseException | XmlParserException | ServerException | NoSuchAlgorithmException |
                 IOException | InvalidResponseException | InvalidKeyException | InternalException |
                 InsufficientDataException e) {
            throw new ServiceException("Failed to delete file", e);
        }
    }

}
