import { BrowserRouter, Routes, Route } from "react-router-dom";
import Dashboard from "./pages/Dashboard";
import VaultDetails from "./pages/VaultDetails";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Dashboard />} />
        <Route path="/vault/:id" element={<VaultDetails />} />
      </Routes>
    </BrowserRouter>
  );
}