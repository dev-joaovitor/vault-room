import { X } from "lucide-react";
import { useState } from "react";

export default function CreateVaultModal({ onClose, onCreate }) {
  const [name, setName] = useState("");

  function handleSubmit(e) {
    e.preventDefault();

    if (!name.trim()) {
      alert("Digite o nome do Vault");
      return;
    }

    onCreate(name);
  }

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/50 px-4">
      <form
        onSubmit={handleSubmit}
        className="w-full max-w-md rounded-xl bg-white p-6 shadow-xl"
      >
        <div className="mb-5 flex items-center justify-between">
          <h2 className="title-font text-xl text-[var(--wine-primary)]">
            Novo Vault
          </h2>

          <button
            type="button"
            onClick={onClose}
            className="text-[var(--wood-medium)] transition hover:text-[var(--wine-primary)]"
          >
            <X size={18} />
          </button>
        </div>

        <label className="mb-2 block text-sm font-semibold text-[var(--text-rich)]">
          Nome do Vault
        </label>

        <input
          className="mb-5 w-full rounded border border-[#d8c7ac] px-3 py-2 outline-none transition focus:border-[var(--wine-primary)]"
          placeholder="Ex: Dispensa Principal"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />

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
            Criar Vault
          </button>
        </div>
      </form>
    </div>
  );
}