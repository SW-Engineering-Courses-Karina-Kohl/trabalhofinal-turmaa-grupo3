export interface CommissionReport {
  id: string;
  status: string;
  filename: string;
  commission_pool: number;
  seller_count: number;
  average_payout: number;
}
