import { Account, CreateAccountRequest, API_BASE_URL } from "./types";

export const getAccounts = async (): Promise<Account[]> => {
  try {
    const response = await fetch(`${API_BASE_URL}/api/accounts`);
    if (!response.ok) {
      // レスポンスのステータスが200番台以外の場合はエラーを投げる
      throw new Error(`勘定科目の取得に失敗しました: ${response.status}`);
    }
    return response.json();
  } catch (error) {
    console.error("勘定科目の取得エラー:", error); // わかりやすくするためにコンソールに出力
    throw error;
  }
};

export const createAccount = async (
  request: CreateAccountRequest,
): Promise<void> => {
  try {
    const response = await fetch(`${API_BASE_URL}/api/accounts`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(request),
    });
    if (!response.ok) {
      throw new Error(`勘定科目の作成に失敗しました: ${response.status}`);
    }
  } catch (error) {
    console.error("勘定科目の作成エラー:", error);
    throw error;
  }
};
