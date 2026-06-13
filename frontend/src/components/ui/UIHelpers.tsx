export const Currency = (c : number) : string => `${c.toLocaleString("pt-BR", { style: "currency", currency: "BRL" })}`
