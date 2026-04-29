import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { ArrowLeft, Edit, Minus, Plus, Trash2 } from "lucide-react";

import Header from "../components/Header";
import CreateProductModal from "../components/CreateProductModal";
import EditProductModal from "../components/EditProductModal";
import RegisterTimeline from "../components/RegisterTimeline";
import EmptyState from "../components/EmptyState";
import Loading from "../components/Loading";
import ErrorMessage from "../components/ErrorMessage";
import { api } from "../services/api";

export default function VaultDetails() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [products, setProducts] = useState([]);
  const [registers, setRegisters] = useState([]);
  const [types, setTypes] = useState([]);
  const [selectedType, setSelectedType] = useState("");

  const [subtotal, setSubtotal] = useState(0);
  const [count, setCount] = useState(0);
  const [activeTab, setActiveTab] = useState("products");

  const [showCreateModal, setShowCreateModal] = useState(false);
  const [editingProduct, setEditingProduct] = useState(null);

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  async function loadTypes() {
    try {
      const response = await api.get("/products/types");
      setTypes(response.data.data || []);
    } catch (error) {
      console.error(error);
    }
  }

  async function loadProducts() {
    const url = selectedType
      ? `/vaults/${id}/products?type=${selectedType}`
      : `/vaults/${id}/products`;

    const response = await api.get(url);
    setProducts(response.data.data || []);
  }

  async function loadSubtotal() {
    const url = selectedType
      ? `/vaults/${id}/subtotal?type=${selectedType}`
      : `/vaults/${id}/subtotal`;

    const response = await api.get(url);
    setSubtotal(response.data.data?.subtotal || 0);
  }

  async function loadCount() {
    const url = selectedType
      ? `/vaults/${id}/products/count?type=${selectedType}`
      : `/vaults/${id}/products/count`;

    const response = await api.get(url);
    setCount(response.data.data?.count || 0);
  }

  async function loadRegisters() {
    const response = await api.get(`/registers/vault/${id}`);
    setRegisters(response.data.data || []);
  }

  async function refreshData() {
    try {
      setLoading(true);
      setError(null);

      await Promise.all([
        loadProducts(),
        loadSubtotal(),
        loadCount(),
        loadRegisters(),
      ]);
    } catch (error) {
      console.error(error);
      setError("Erro ao carregar dados. Verifique se o backend está rodando.");
    } finally {
      setLoading(false);
    }
  }

  async function changeQuantity(productId, action) {
    try {
      await api.patch(`/products/${productId}/quantity`, {
        quantity: 1,
        type_id: action,
      });

      refreshData();
    } catch (error) {
      console.error(error);
      alert("Erro ao alterar quantidade");
    }
  }

  async function deleteProduct(productId) {
    const ok = confirm("Deseja remover este produto?");
    if (!ok) return;

    try {
      await api.delete(`/products/${productId}`);
      refreshData();
    } catch (error) {
      console.error(error);
      alert("Erro ao remover produto");
    }
  }

  function formatMoney(value) {
    return Number(value || 0).toLocaleString("pt-BR", {
      style: "currency",
      currency: "BRL",
    });
  }

  function formatDate(date) {
    if (!date) return "-";
    return new Date(date).toLocaleDateString("pt-BR");
  }

  useEffect(() => {
    loadTypes();
  }, []);

  useEffect(() => {
    refreshData();
  }, [id, selectedType]);

  return (
    <div className="min-h-screen bg-[var(--parchment-bg)]">
      <Header />

      <main className="mx-auto max-w-6xl px-6 py-8">
        <button
          onClick={() => navigate("/")}
          className="mb-6 flex items-center gap-2 text-sm text-[var(--wine-primary)] transition hover:brightness-75"
        >
          <ArrowLeft size={16} />
          Voltar
        </button>

        <div className="mb-6 flex items-center justify-between gap-4">
          <div>
            <h1 className="title-font text-2xl text-[var(--wine-primary)]">
              Detalhes do Vault
            </h1>
            <p className="text-sm text-[var(--wood-medium)]">
              Produtos e controle financeiro
            </p>
          </div>

          <button
            onClick={() => setShowCreateModal(true)}
            className="flex items-center gap-2 rounded bg-[var(--wine-primary)] px-4 py-2 text-sm text-white shadow transition hover:brightness-90"
          >
            <Plus size={16} />
            Adicionar Produto
          </button>
        </div>

        {loading && <Loading text="Carregando dados do Vault..." />}

        {error && <ErrorMessage message={error} />}

        <section className="mb-6 grid grid-cols-1 gap-4 md:grid-cols-3">
          <div className="rounded bg-white p-5 shadow">
            <p className="text-sm text-[var(--wood-medium)]">
              Total de Produtos
            </p>
            <strong className="text-2xl text-[var(--wine-primary)]">
              {products.length}
            </strong>
          </div>

          <div className="rounded bg-white p-5 shadow">
            <p className="text-sm text-[var(--wood-medium)]">
              Unidades em Estoque
            </p>
            <strong className="text-2xl text-[var(--wine-primary)]">
              {count}
            </strong>
          </div>

          <div className="rounded bg-white p-5 shadow">
            <p className="text-sm text-[var(--wood-medium)]">Valor Total</p>
            <strong className="text-2xl text-[var(--wine-primary)]">
              {formatMoney(subtotal)}
            </strong>
          </div>
        </section>

        <div className="mb-4 flex gap-2">
          <button
            onClick={() => setActiveTab("products")}
            className={`rounded px-4 py-2 transition ${
              activeTab === "products"
                ? "bg-[var(--wine-primary)] text-white"
                : "bg-white text-[var(--wine-primary)] hover:bg-[var(--parchment-bg)]"
            }`}
          >
            Produtos
          </button>

          <button
            onClick={() => setActiveTab("history")}
            className={`rounded px-4 py-2 transition ${
              activeTab === "history"
                ? "bg-[var(--wine-primary)] text-white"
                : "bg-white text-[var(--wine-primary)] hover:bg-[var(--parchment-bg)]"
            }`}
          >
            Histórico
          </button>
        </div>

        {activeTab === "products" && (
          <div className="rounded bg-white p-5 shadow">
            <h2 className="title-font mb-4 text-xl text-[var(--wine-primary)]">
              Produtos
            </h2>

            <div className="mb-4 flex flex-wrap gap-2">
              <button
                onClick={() => setSelectedType("")}
                className={`rounded px-3 py-2 text-sm transition ${
                  selectedType === ""
                    ? "bg-[var(--wine-primary)] text-white"
                    : "bg-[var(--parchment-bg)] text-[var(--wine-primary)] hover:brightness-95"
                }`}
              >
                Todos
              </button>

              {types.map((type) => (
                <button
                  key={type.id}
                  onClick={() => setSelectedType(type.id)}
                  className={`rounded px-3 py-2 text-sm transition ${
                    selectedType === type.id
                      ? "bg-[var(--wine-primary)] text-white"
                      : "bg-[var(--parchment-bg)] text-[var(--wine-primary)] hover:brightness-95"
                  }`}
                >
                  {type.name}
                </button>
              ))}
            </div>

            <div className="overflow-x-auto">
              <table className="w-full text-left text-sm">
                <thead>
                  <tr className="border-b text-[var(--wine-primary)]">
                    <th className="py-3">Nome</th>
                    <th>Tipo</th>
                    <th>Quantidade</th>
                    <th>Preço/Unidade</th>
                    <th>Total</th>
                    <th>Atualizado</th>
                    <th>Ações</th>
                  </tr>
                </thead>

                <tbody>
                  {products.map((p) => (
                    <tr
                      key={p.id}
                      className="border-b transition hover:bg-[var(--parchment-bg)]"
                    >
                      <td className="py-3 font-medium text-[var(--text-rich)]">
                        {p.name}
                      </td>

                      <td className="text-[var(--text-rich)]">
                        {p.type?.name || "-"}
                      </td>

                      <td>
                        <div className="flex items-center gap-2">
                          <button
                            onClick={() => changeQuantity(p.id, "subtract")}
                            className="rounded bg-[var(--parchment-bg)] p-1 text-[var(--wine-primary)] transition hover:brightness-90"
                          >
                            <Minus size={14} />
                          </button>

                          <span className="text-[var(--text-rich)]">
                            {p.quantity}
                          </span>

                          <button
                            onClick={() => changeQuantity(p.id, "add")}
                            className="rounded bg-[var(--parchment-bg)] p-1 text-[var(--wine-primary)] transition hover:brightness-90"
                          >
                            <Plus size={14} />
                          </button>
                        </div>
                      </td>

                      <td className="text-[var(--text-rich)]">
                        {Number(p.price_by_unit) === 0 ? (
                          <span className="rounded bg-[var(--vault-gold)]/20 px-2 py-1 text-xs font-bold text-[var(--wood-medium)]">
                            Grátis
                          </span>
                        ) : (
                          formatMoney(p.price_by_unit)
                        )}
                      </td>

                      <td className="text-[var(--text-rich)]">
                        {formatMoney(p.total_price)}
                      </td>

                      <td className="text-[var(--wood-medium)]">
                        {formatDate(p.updated_at)}
                      </td>

                      <td>
                        <div className="flex gap-2">
                          <button
                            onClick={() => setEditingProduct(p)}
                            className="text-[var(--wine-primary)] transition hover:brightness-75"
                          >
                            <Edit size={16} />
                          </button>

                          <button
                            onClick={() => deleteProduct(p.id)}
                            className="text-red-700 transition hover:text-red-900"
                          >
                            <Trash2 size={16} />
                          </button>
                        </div>
                      </td>
                    </tr>
                  ))}

                  {products.length === 0 && (
                    <tr>
                      <td colSpan="7" className="py-6">
                        <EmptyState
                          title="Nenhum produto encontrado"
                          description="Adicione produtos para começar a controlar este Vault."
                          action={
                            <button
                              onClick={() => setShowCreateModal(true)}
                              className="rounded bg-[var(--wine-primary)] px-4 py-2 text-sm text-white shadow transition hover:brightness-90"
                            >
                              Adicionar Produto
                            </button>
                          }
                        />
                      </td>
                    </tr>
                  )}
                </tbody>
              </table>
            </div>
          </div>
        )}

        {activeTab === "history" && (
          <div className="rounded bg-white p-5 shadow">
            <h2 className="title-font mb-4 text-xl text-[var(--wine-primary)]">
              Histórico de Atividades
            </h2>

            <RegisterTimeline registers={registers} />
          </div>
        )}
      </main>

      {showCreateModal && (
        <CreateProductModal
          vaultId={id}
          onClose={() => setShowCreateModal(false)}
          onCreated={refreshData}
        />
      )}

      {editingProduct && (
        <EditProductModal
          product={editingProduct}
          onClose={() => setEditingProduct(null)}
          onUpdated={refreshData}
        />
      )}
    </div>
  );
}