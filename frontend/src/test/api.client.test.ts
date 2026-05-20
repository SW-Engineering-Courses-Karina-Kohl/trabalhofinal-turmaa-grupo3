/**
 * api/client.test.ts
 *
 * Tests for the base HTTP client.
 * TODO (implementor): add tests for auth token injection when implemented.
 */

import { describe, it, expect, vi, beforeEach } from "vitest";
import { ApiError } from "@/api/client";

// We re-import http after mocking fetch so it picks up our mock
beforeEach(() => {
  vi.stubGlobal("fetch", vi.fn());
});

describe("ApiError", () => {
  it("has the correct name", () => {
    const err = new ApiError(404, "NOT_FOUND", "Not found");
    expect(err.name).toBe("ApiError");
    expect(err.status).toBe(404);
    expect(err.code).toBe("NOT_FOUND");
    expect(err.message).toBe("Not found");
  });
});

describe("http.get", () => {
  it("TODO: returns parsed JSON on 200", () => {
    // TODO (implementor): stub fetch to return { ok: true, json: async () => data }
    // and assert http.get('/endpoint') resolves to data.
    expect(true).toBe(true);
  });

  it("TODO: throws ApiError on non-ok response", () => {
    // TODO (implementor): stub fetch to return { ok: false, status: 404, json: ... }
    // and assert http.get('/missing') rejects with ApiError.
    expect(true).toBe(true);
  });
});

describe("http.postForm", () => {
  it("TODO: sends FormData without Content-Type header override", () => {
    // TODO (implementor): assert that fetch is called with a FormData body
    // and no Content-Type header (so browser sets boundary automatically).
    expect(true).toBe(true);
  });
});
