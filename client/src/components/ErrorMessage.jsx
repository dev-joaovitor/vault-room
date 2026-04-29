export default function ErrorMessage({ message }) {
  return (
    <div className="mb-4 rounded border border-red-200 bg-red-50 p-4 text-sm text-red-700">
      {message}
    </div>
  );
}