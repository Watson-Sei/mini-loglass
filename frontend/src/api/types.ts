// APIの型定義を定義したファイル

// backendのスキーマと整合性を取るために、openapi.yamlなどのスキーマから型を生成するなどの方法も考えられる
// 本来であれば、.env.local等に設定しておくのが理想
export const API_BASE_URL =
  process.env.NEXT_PUBLIC_API_BASE_URL || "http://localhost:8080";

// Base types from backend
export type AccountCode = {
  value: string;
};

export type AccountName = {
  value: string;
};

// Account types
export enum AccountType {
  PROFIT = "PROFIT",
  LOSS = "LOSS",
  ASSET = "ASSET",
  LIABILITY = "LIABILITY",
}

export type Account = {
  code: AccountCode;
  name: AccountName;
  accountType: AccountType;
  parentCode?: AccountCode;
};

export type CreateAccountRequest = {
  code: string;
  name: string;
  accountType: AccountType;
  parentCode?: string;
};

// Team types
export type TeamId = {
  value: number;
};

export type TeamName = {
  value: string;
};

export type Team = {
  id: TeamId;
  name: TeamName;
};
