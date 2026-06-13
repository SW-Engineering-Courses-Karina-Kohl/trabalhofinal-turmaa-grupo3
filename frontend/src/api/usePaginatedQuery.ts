import { useState } from 'react'
import { PaginatedResult } from '@/types'
import { useQuery, keepPreviousData, QueryKey } from '@tanstack/react-query'

interface UsePaginatedQueryOptions<T> {
  queryKey:    QueryKey
  queryFn:     (page: number, pageSize: number) => Promise<PaginatedResult<T>>
  initialPage?: number
  pageSize?:    number
  staleTime?:   number
}

export function usePaginatedQuery<T>({
  queryKey,
  queryFn,
  initialPage = 1,
  pageSize    = 20,
  staleTime   = 1000 * 60 * 5,
}: UsePaginatedQueryOptions<T>) {
  const [page, setPage] = useState(initialPage)

  const query = useQuery({
    queryKey: [...queryKey, { page, pageSize }],
    queryFn:  () => queryFn(page, pageSize),
    placeholderData: keepPreviousData, // no flash between pages
    staleTime,
  })

  const goTo     = (n: number) => setPage(n)
  const nextPage = () => setPage(p => p + 1)
  const prevPage = () => setPage(p => p - 1)

  return {
    ...query,
    items: query.data?.data,
    page,
    pageSize,
    goTo,
    nextPage,
    prevPage,
    canNext: page < (query.data?.totalPages ?? 0),
    canPrev: page > 1,
  }
}

