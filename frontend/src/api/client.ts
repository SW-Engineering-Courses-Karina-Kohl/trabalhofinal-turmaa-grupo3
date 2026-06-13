/**
 * api/client.ts
 *
 * Base HTTP client. The baseURL is read from the VITE_API_BASE_URL env var so
 * it works both locally (via Vite proxy or direct) and inside Docker Compose.
 *
 */

import { BACKEND_URL } from "@/config/config";

const BASE_URL = BACKEND_URL;

export class ApiError extends Error {
  constructor(
    public readonly status: number,
    public readonly code: string | undefined,
    message: string,
  ) {
    super(message);
    this.name = "ApiError";
  }
}

async function handleResponse<T>(res: Response): Promise<T> {
  if (!res.ok) {
    let message = `HTTP ${res.status}`;
    let code: string | undefined;
    try {
      const body = await res.json();
      message = body.message ?? message;
      code = body.code;
    } catch {
      // non-JSON error body — keep default message
    }
    throw new ApiError(res.status, code, message);
  }
  return res.json() as Promise<T>;
}

export const http = {
  get<T>(path: string, params?: Record<string, string | number | undefined>): Promise<T> {
    const url = new URL(`${BASE_URL}${path}`);
    if (params) {
      Object.entries(params).forEach(([k, v]) => {
        if (v !== undefined) url.searchParams.set(k, String(v));
      });
    }
    return fetch(url.toString()).then((r) => handleResponse<T>(r));
  },

  post<T>(path: string, body: unknown): Promise<T> {
    return fetch(`${BASE_URL}${path}`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body),
    }).then((r) => handleResponse<T>(r));
  },

  postForm<T>(path: string, form: FormData): Promise<T> {
    return fetch(`${BASE_URL}${path}`, {
      method: "POST",
      body: form,
    }).then((r) => handleResponse<T>(r));
  },

  delete<T>(path: string): Promise<T> {
    return fetch(`${BASE_URL}${path}`, { method: "DELETE" }).then((r) =>
      handleResponse<T>(r),
    );
  },
};
