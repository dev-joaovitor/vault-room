import { X } from "lucide-react";
import { useEffect, useState } from "react";
import { api } from "../services/api";

export default function CreateProductModal({ vaultId, onClose, onCreated }) {
  const [name, setName] = useState("");
  const [quantity, setQuantity] = useState(1);
  const [price, setPrice] = useState("");
  const [types, setTypes] = useState([]);
  const [selectedType, setSelectedType] = useState(null);

  async function loadTypes() {
    try {
      const res = await api.get("/products/types");
      setTypes(res.data.data || []);
    } catch (err) {
      console.error(err);
    }
  }

  async function handleSubmit(e) {
    e.preventDefault();

    if (!name.trim() || !quantity || !selectedType) {
      alert("Preencha todos os campos");
      return;
    }

    try {
      await api.post("/products", {
        name,
        quantity: Number(quantity),
        price_by_unit: Number(price || 0),
        vault_id: Number(vaultId),
        type_id: selectedType,
      });

      onCreated();
      onClose();
    } catch (err) {
      console.error(err);
      alert("Erro ao criar produto");
    }
  }

  useEffect(() => {
    loadTypes();
  }, []);

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/50 px-4">
      <form
        onSubmit={handleSubmit}
        className="w-full max-w-lg rounded-xl bg-white p-6 shadow-xl"
      >
        <div className="mb-5 flex items-center justify-between">
          <h2 className="title-font text-xl text-[var(--wine-primary)]">
            Adicionar Produto
          </h2>

          <button
            type="button"
            onClick={onClose}
            className="text-[var(--wood-medium)] transition hover:text-[var(--wine-primary)]"
          >
            <X size={18} />
          </button>
        </div>

        <label className="mb-1 block text-sm font-semibold text-[var(--text-rich)]">
          Nome do Produto *
        </label>
        <input
          className="mb-3 w-full rounded border border-[#d8c7ac] px-3 py-2 outline-none focus:border-[var(--wine-primary)]"
          placeholder="Ex: Arroz Integral"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />

        <label className="mb-2 block text-sm font-semibold text-[var(--text-rich)]">
          Tipo de Produto *
        </label>

        <div className="mb-3 grid grid-cols-2 gap-2 md:grid-cols-3">
          {types.map((type) => (
            <button
              key={type.id}
              type="button"
              onClick={() => setSelectedType(type.id)}
              className={`rounded border p-2 text-sm transition ${
                selectedType === type.id
                  ? "border-[var(--wine-primary)] bg-[var(--wine-primary)] text-white"
                  : "border-[#d8c7ac] bg-[var(--parchment-bg)] text-[var(--wine-primary)] hover:bg-[#eadcc4]"
              }`}
            >
              {type.name}
            </button>
          ))}
        </div>

        <div className="mb-4 flex gap-2">
          <div className="w-1/2">
            <label className="mb-1 block text-sm font-semibold text-[var(--text-rich)]">
              Quantidade *
            </label>
            <input
              type="number"
              min="1"
              className="w-full rounded border border-[#d8c7ac] px-3 py-2 outline-none focus:border-[var(--wine-primary)]"
              value={quantity}
              onChange={(e) => setQuantity(e.target.value)}
            />
          </div>

          <div className="w-1/2">
            <label className="mb-1 block text-sm font-semibold text-[var(--text-rich)]">
              Preço por Unidade *
            </label>
            <input
              type="number"
              step="0.01"
              min="0"
              className="w-full rounded border border-[#d8c7ac] px-3 py-2 outline-none focus:border-[var(--wine-primary)]"
              value={price}
              onChange={(e) => setPrice(e.target.value)}
            />
          </div>
        </div>

        <div className="flex justify-end gap-2">
          <button
            type="button"
            onClick={onClose}
            className="rounded bg-[var(--wood-medium)] px-4 py-2 text-sm text-white transition hover:brightness-90"
          >
            Cancelar
          </button>

          <button
            type="submit"
            className="rounded bg-[var(--wine-primary)] px-4 py-2 text-sm text-white transition hover:brightness-90"
          >
            Adicionar
          </button>
        </div>
      </form>
    </div>
  );
}