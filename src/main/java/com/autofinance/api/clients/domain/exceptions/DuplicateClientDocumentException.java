package com.autofinance.api.clients.domain.exceptions;

import com.autofinance.api.clients.domain.model.valueobjects.DocumentId;
import com.autofinance.api.shared.domain.exceptions.DomainException;

/** Raised when registering a client whose identity document already exists in the dealership. */
public class DuplicateClientDocumentException extends DomainException {
    public DuplicateClientDocumentException(DocumentId documentId) {
        super(ClientErrorCode.DUPLICATE_CLIENT_DOCUMENT,
                "A client with document %s %s already exists in this dealership"
                        .formatted(documentId.type(), documentId.number()));
    }
}
