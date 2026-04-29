export default function Loading({ text = "Carregando..." }) {
  return (
    <div className="mb-4 flex items-center gap-3 rounded bg-white p-4 text-sm text-[var(--wood-medium)] shadow">
      <div className="h-4 w-4 animate-spin rounded-full border-2 border-[var(--vault-gold)] border-t-transparent" />
      {text}
    </div>
  );
}