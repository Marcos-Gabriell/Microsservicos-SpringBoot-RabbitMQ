package br.com.marcos.pedidos.notificacao.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@AllArgsConstructor
@NonNull
@Data
public class ItemPedido {

    private UUID id = UUID.randomUUID();

    private Produto produto;

    private Integer quantidade;
}
