import { PackagePlus, PackageMinus, Pencil } from "lucide-react";

export default function RegisterTimeline({ registers }) {
  function getStyle(typeName = "") {
    const type = typeName.toLowerCase();

    if (type.includes("add") || type.includes("adicionado")) {
      return {
        icon: <PackagePlus size={18} />,
        color: "text-green-700",
        bg: "bg-green-100",
        label: "Adicionado",
      };
    }

    if (type.includes("remove") || type.includes("removido")) {
      return {
        icon: <PackageMinus size={18} />,
        color: "text-red-700",
        bg: "bg-red-100",
        label: "Removido",
      };
    }

    return {
      icon: <Pencil size={18} />,
      color: "text-yellow-700",
      bg: "bg-yellow-100",
      label: "Alterado",
    };
  }

  if (!registers || registers.length === 0) {
    return (
      <p className="py-6 text-center text-sm text-gray-500">
        Nenhum registro encontrado.
      </p>
    );
  }

  return (
    <div className="space-y-4">
      {registers.map((register) => {
        const style = getStyle(register.register_type?.name);

        return (
          <div
            key={register.id}
            className="flex gap-4 rounded border border-[#eadcc4] bg-[#fffaf0] p-4"
          >
            <div
              className={`flex h-10 w-10 items-center justify-center rounded-full ${style.bg} ${style.color}`}
            >
              {style.icon}
            </div>

            <div>
              <div className="mb-1 flex flex-wrap items-center gap-2">
                <strong className={style.color}>{style.label}</strong>

                {register.product?.type?.name && (
                  <span className="rounded bg-[#f3ead7] px-2 py-1 text-xs text-[#6b1d2c]">
                    {register.product.type.name}
                  </span>
                )}
              </div>

              <p className="text-sm text-[#3a2418]">
                {register.description}
              </p>

              {register.product?.name && (
                <p className="mt-1 text-xs text-gray-500">
                  Produto: {register.product.name}
                </p>
              )}
            </div>
          </div>
        );
      })}
    </div>
  );
}
