# Vault Room (pt-BR)
Software de gerenciamento de estoque para a aula de **Programação Orientada a Objetos II**.

# Tecnologias
- Docker (containerização)
- PostgreSQL (banco de dados)
- Kotlin (linguagem servidor)
- Spring Boot (framework servidor)
- React (biblioteca frontend)
- Vite (biblioteca de build frontend)

<details>
  <summary><h1>API</h1></summary>

## Endpoint Base: /api/v1/
## Retorno base
```js
/* Sucesso */
{
  success: true,
  message: "",
  data: {}
}

/* Erro */
{
  success: false,
  message: "Error",
  data: null
}
```
## Endpoints

<details>
  <summary><h3>Vaults</h3></summary>

### POST /vaults - Cria um _vault_ (estoque).
- Espera (body):
```js
{
  name: "string, maximo 100 caracteres"
}
```
- Retorna (response):
```js
// HTTP 201 CREATED ou 400 BAD REQUEST
{
  data: {
    id: 321,
    name: "my vault",
    product_quantity: 0,
    created_at: "timestamp",
    updated_at: "timestamp",
    deleted_at: null,
  }
}
```

### GET /vaults - Lista todos os vaults.
- Retorna (response):
```js
// HTTP 200 OK
{
  data: [
    {
      id: 321,
      name: "my vault",
      product_quantity: 0,
      created_at: "timestamp",
      updated_at: "timestamp",
      deleted_at: null,
    },
    // ...resto
  ]
}
```

### PATCH /vaults/`<id>` - Atualiza um Vault.
- Espera (body):
```js
{
  name: "string, maximo 100 caracteres"
}
```
- Retorna (response):
```js
// HTTP 200 OK ou 400 BAD REQUEST
{
  data: {
    id: 321,
    name: "my vault",
    product_quantity: 666,
    created_at: "timestamp",
    updated_at: "timestamp",
    deleted_at: null,
  }
}
```

### DELETE /vaults/`<id>` - Remove um Vault.
- Retorna (response):
```js
// HTTP 200 ou 400 BAD REQUEST
{
  data: null
}
```

### GET /vaults/`<id>`/products - Lista os produtos do vault
- Retorna (response):
```js
// HTTP 200 ou 400
{
  data: [
    {
      id: 312,
      name: "my product",
      quantity: 8,
      total_price: 40.0,
      price_by_unit: 5,
      created_at: "timestamp",
      updated_at: "timestamp",
      deleted_at: null,
      type: {
        name: "Hortifruti"
      },
    }
  ],
}
```

### GET /vaults/`<id>`/products?type=321 - Lista os produtos do vault filtrado por `type`
- Retorna (response):
```js
// HTTP 200 ou 400
{
  data: [
    {
      id: 312,
      name: "my product",
      quantity: 8,
      total_price: 40.0,
      price_by_unit: 5,
      created_at: "timestamp",
      updated_at: "timestamp",
      deleted_at: null,
      type: {
        name: "Hortifruti" // obrigatorio: ser o mesmo tipo que foi enviado no param `type`
      },
    }
  ],
}
```

### GET /vaults/`<id>`/products/count - Retorna a quantidade de produtos ativos de um Vault
- Retorna (response):
```js
// HTTP 200 ou 400
{
  data: {
    count: 321
  }
}
```

### GET /vaults/`<id>`/products/count?type=321 - Retorna a quantidade de produtos de um tipo especifico ativos de um Vault
- Retorna (response):
```js
// HTTP 200 ou 400
{
  data: {
    count: 321
  }
}
```

### GET /vaults/`<id>`/subtotal - Retorna o valor em produtos de um Vault.
- Retorna (response):
```js
// HTTP 200 ou 400
{
  data: {
    subtotal: 4000.12
  }
}
```

### GET /vaults/`<id>`/subtotal?type=321 - Retorna o valor em produtos de um tipo especifico de um Vault.
- Retorna (response):
```js
// HTTP 200 ou 400
{
  data: {
    subtotal: 2500.30
  }
}
```

</details>

---

<details>
  <summary><h3>Products</h3></summary>

### POST /products - Cria um Product.
- Espera (body):
```js
{
  name: "string, maximo 100 caracteres",
  quantity: "integer, maximo 9999, minimo 1",
  price_by_unit: "double, maximo 9999.99, minimo 0.01",
  vault_id: "integer, deve existir",
  type_id: "integer, deve existir",
}
```
- Retorna (response):
```js
// HTTP 201 CREATED ou 400 BAD REQUEST
{
  data: {
    name: "manteiga 200g cruzilia sem sal",
    quantity: 3,
    price_by_unit: 12.99,
    total_price: 38.97,
    vault: {
      name: "my vault",
    },
    type: {
      name: "Proteinas e Carnes",
    },
    created_at: "timestamp",
    updated_at: "timestamp",
    deleted_at: null,
  }
}
```

### PATCH /products/`<id>` - Atualiza dados basico de um Product.
- Espera (body):
```js
{
  name: "string, maximo 100 caracteres",
  price_by_unit: "double, maximo 9999.99, minimo 0.01",
  type_id: "integer, deve existir",
}
```
- Retorna (response):
```js
// HTTP 200 CREATED ou 400 BAD REQUEST
{
  data: {
    name: "manteiga 200g cruzilia com sal",
    quantity: 3,
    price_by_unit: 12.99,
    total_price: 38.97,
    vault: {
      name: "my vault",
    },
    type: {
      name: "Proteinas e Carnes",
    },
    created_at: "timestamp",
    updated_at: "timestamp",
    deleted_at: null,
  }
}
```

### PATCH /products/`<id>`/quantity - Atualiza a quantidade de um Product.
- Espera (body):
```js
{
  quantity: "integer, maximo 9999, minimo 1",
  type_id: "register type id: add, change, subtract"
  /*
   * add: vai adicionar ao que ja tem
   * change: vai atualizar completamente
   * subtract: vai subtrair do que ja tem
   */
}
```
- Retorna (response):
```js
// HTTP 200 OK ou 400 BAD REQUEST
{
  data: {
    new_quantity: 31
  }
}
```

### DELETE /products/`<id>` - Remove um Product.
- Retorna (response):
```js
// HTTP 200 ou 400 BAD REQUEST
{
  data: null
}
```
  
</details>

---

<details>
  <summary><h3>Registers</h3></summary>

### GET /registers/vault/`<vault-id>` - Retorna os registros de um Vault
- Retorna (response):
```js
// HTTP 200 ou 400
{
  data: [
    {
      id: 432,
      desc_register: "De 4 para 5",
      product: {
        name: "agua mineral sem gas 500ml",
        type: {
          name: "Bebidas",
        },
      },
      register_type: {
        name: "Subtrair",
      },
    },
    // ...resto
  ]
}
```

### GET /registers/product/`<product-id>` - Retorna os registros de um Product
- Retorna (response):
```js
// HTTP 200 ou 400
{
  data: [
    {
      id: 432,
      desc_register: "De 4 para 5",
      vault: {
        name: "my vault",
      },
      register_type: {
        name: "Subtrair",
      },
    },
    // ...resto
  ]
}
```

### GET /registers/product/`<product-id>`?product_type=432 - Retorna os registros de um Product de um tipo especifico
- Retorna (response):
```js
// HTTP 200 ou 400
{
  data: [
    {
      id: 432,
      desc_register: "De 4 para 5",
      vault: {
        name: "my vault",
      },
      register_type: {
        name: "Subtrair",
      },
    },
    // ...resto
  ]
}
```

</details>

---

<details>
  <summary><h3>Product Types</h3></summary>

### GET /products/types - Retorna os tipos disponiveis de um produto
- Retorna (response):
```js
// HTTP 200 ou 400
{
  data: [
    {
      id: 321,
      name: "Laticinios e Ovos",
    },
    // ...resto
  ]
}
```
</details>

---

<details>
  <summary><h3>Register Types</h3></summary>

### GET /registers/types - Retorna os tipos disponiveis de um registro
- Retorna (response):
```js
// HTTP 200 ou 400
{
  data: [
    {
      id: 555,
      name: "Subtrair",
    },
    // ...resto
  ]
}
```

</details>

---

</details>
