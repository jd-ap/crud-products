package io.tech.proof.error;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logmanager.Level;

import java.util.NoSuchElementException;
import java.util.logging.Logger;

@Provider
public class NoSuchElementExceptionMapper implements ExceptionMapper<NoSuchElementException> {

    private Logger logger = Logger.getLogger(NoSuchElementExceptionMapper.class.getName());

    @Override
    public Response toResponse(NoSuchElementException e) {
        logger.log(Level.INFO, e.getMessage());

        return Response.status(Response.Status.NOT_FOUND)
                .build();
    }
}
