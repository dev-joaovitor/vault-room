export default function EmptyState({ title, description, action }) {
  return (
    <div className="flex flex-col items-center justify-center rounded-xl bg-white p-10 text-center shadow">
      <div className="mb-4 flex h-16 w-16 items-center justify-center rounded-full bg-[var(--parchment-bg)] text-2xl">
        🏛️
      </div>

      <h2 className="title-font text-xl text-[var(--wine-primary)]">
        {title}
      </h2>

      <p className="mt-2 max-w-md text-sm text-[var(--wood-medium)]">
        {description}
      </p>

      {action && <div className="mt-5">{action}</div>}
    </div>
  );
}