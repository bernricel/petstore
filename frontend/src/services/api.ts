function resolveApiBaseUrl() {
  const configuredUrl = import.meta.env.VITE_API_BASE_URL;

  if (!configuredUrl) {
    return "http://localhost:8080/api/musngi/catalog";
  }

  return configuredUrl.endsWith("/api/musngi/catalog")
    ? configuredUrl
    : `${configuredUrl.replace(/\/$/, "")}/api/musngi/catalog`;
}

const API_BASE_URL = resolveApiBaseUrl();

export async function apiFetch<T>(path: string, init?: RequestInit): Promise<T> {
  const response = await fetch(`${API_BASE_URL}${path}`, {
    headers: {
      "Content-Type": "application/json",
    },
    ...init,
  });

  if (!response.ok) {
    const fallbackMessage = `Request failed with status ${response.status}`;
    let message = fallbackMessage;

    try {
      const errorBody = (await response.json()) as { message?: string };
      message = errorBody.message ?? fallbackMessage;
    } catch {
      message = fallbackMessage;
    }

    throw new Error(message);
  }

  if (response.status === 204) {
    return undefined as T;
  }

  return (await response.json()) as T;
}
