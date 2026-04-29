import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Plus, Trash2 } from "lucide-react";

import Header from "../components/Header";
import CreateVaultModal from "../components/CreateVaultModal";
import EmptyState from "../components/EmptyState";
import Loading from "../components/Loading";
import ErrorMessage from "../components/ErrorMessage";
import { api } from "../services/api";

export default function Dashboard() {
  const [vaults, setVaults] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const navigate = useNavigate();

  async function loadVaults() {
    try {
      setLoading(true);
      setError(null);

      const res = await api.get("/vaults");
      setVaults(res.data.data || []);
    } catch (err) {
      console.error(err);
      setError(
        "Não foi possível carregar os Vaults. Verifique se o backend está rodando."
      );
    } finally {
      setLoading(false);
    }
  }

  async function createVault(name) {
    try {
      await api.post("/vaults", { name });
      setShowModal(false);
      loadVaults();
    } catch (err) {
      console.error(err);
      alert("Erro ao criar vault");
    }
  }

  async function deleteVault(id, event) {
    event.stopPropagation();

    const confirmDelete = confirm("Deseja remover este Vault?");
    if (!confirmDelete) return;

    try {
      await api.delete(`/vaults/${id}`);
      loadVaults();
    } catch (err) {
      console.error(err);
      alert("Erro ao deletar vault");
    }
  }

  function formatDate(date) {
    if (!date) return "-";
    return new Date(date).toLocaleDateString("pt-BR");
  }

  useEffect(() => {
    loadVaults();
  }, []);

  return (
    <div className="min-h-screen bg-[var(--parchment-bg)]">
      <Header />

      <main className="mx-auto max-w-6xl px-6 py-8">
        <div className="mb-6 flex items-start justify-between gap-4">
          <div>
            <h1 className="title-font text-2xl text-[var(--wine-primary)]">
              Seus Vaults
            </h1>

            <p className="mt-1 text-sm text-[var(--wood-medium)]">
              Gerencie seus estoques de forma organizada
            </p>
          </div>

          <button
            onClick={() => setShowModal(true)}
            className="flex items-center gap-2 rounded bg-[var(--wine-primary)] px-4 py-2 text-sm text-white shadow transition hover:brightness-90"
          >
            <Plus size={16} />
            Criar Novo Vault
          </button>
        </div>

        {loading && <Loading text="Carregando Vaults..." />}

        {error && <ErrorMessage message={error} />}

        {!loading && !error && vaults.length === 0 && (
          <EmptyState
            title="Nenhum Vault encontrado"
            description="Crie seu primeiro Vault para começar a organizar seus estoques."
            action={
              <button
                onClick={() => setShowModal(true)}
                className="rounded bg-[var(--wine-primary)] px-4 py-2 text-sm text-white shadow transition hover:brightness-90"
              >
                Criar primeiro Vault
              </button>
            }
          />
        )}

        {!loading && !error && vaults.length > 0 && (
          <div className="grid grid-cols-1 gap-6 md:grid-cols-2 lg:grid-cols-3">
            {vaults.map((vault) => (
              <div
                key={vault.id}
                onClick={() => navigate(`/vault/${vault.id}`)}
                className="relative cursor-pointer rounded-xl bg-white p-5 shadow-md transition hover:-translate-y-1 hover:shadow-lg"
              >
                <button
                  onClick={(e) => deleteVault(vault.id, e)}
                  className="absolute right-4 top-4 text-[var(--wood-medium)] transition hover:text-red-700"
                >
                  <Trash2 size={16} />
                </button>

                <h2 className="title-font mb-4 pr-8 text-lg text-[var(--wine-primary)]">
                  {vault.name}
                </h2>

                <p className="text-sm text-[var(--text-rich)]">
                  {vault.product_quantity ?? 0} produtos
                </p>

                <p className="mt-1 text-sm text-[var(--wood-medium)]">
                  Criado em {formatDate(vault.created_at)}
                </p>
              </div>
            ))}
          </div>
        )}
      </main>

      {showModal && (
        <CreateVaultModal
          onClose={() => setShowModal(false)}
          onCreate={createVault}
        />
      )}
    </div>
  );
}