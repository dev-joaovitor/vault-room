import { Vault } from "lucide-react";

export default function Header() {
  return (
    <header className="h-16 bg-[var(--wood-dark)] text-[var(--vault-gold)] shadow-md">
      <div className="mx-auto flex h-full max-w-6xl items-center justify-between px-6">
        <div className="flex items-center gap-2">
          <Vault size={22} />
          <span className="title-font text-lg tracking-widest">
            VAULT ROOM
          </span>
        </div>

        <p className="text-xs text-[#e8dcc7]">
          Controle de itens no seu estoque
        </p>
      </div>
    </header>
  );
}