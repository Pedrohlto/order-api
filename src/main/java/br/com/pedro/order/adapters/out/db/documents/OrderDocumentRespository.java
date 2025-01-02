package br.com.pedro.order.adapters.out.db.documents;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderDocumentRespository extends MongoRepository<OrderDocument, String> {
}
