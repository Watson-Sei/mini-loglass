"use client";

// 適切にコンポーネントを分割して設計する方法もあるが、今回はシンプルに実装した

import { useEffect, useState } from "react";
import { getAccounts } from "@/api/accounts";
import { Account, AccountType } from "@/api/types";

function convertAccountType(accountType: AccountType): string {
  switch (accountType) {
    case "PROFIT" as AccountType:
      return "収益";
    case "LOSS" as AccountType:
      return "費用";
    case "ASSET" as AccountType:
      return "資産";
    case "LIABILITY" as AccountType:
      return "負債";
    default:
      return "";
  }
}

export default function AccountContainer() {
  const [accounts, setAccounts] = useState<Account[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchAccounts = async () => {
      try {
        const data = await getAccounts();
        setAccounts(data);
      } catch (err) {
        setError(
          err instanceof Error ? err.message : "勘定科目の取得に失敗しました",
        );
      } finally {
        setLoading(false);
      }
    };

    fetchAccounts();
  }, []);

  if (loading) return <div>読み込み中...</div>;
  if (error) return <div>エラー: {error}</div>;
  return (
    <>
      <div className="w-full h-14 border-b border-gray-300 flex justify-between items-center">
        <h1 className="mx-10 text-lg leading-14 text-gray-700 font-normal">
          科目一覧
        </h1>
        <a
          href="/accounts/create"
          className="mx-10 px-4 py-2 bg-blue-400 text-white rounded"
        >
          新規作成
        </a>
      </div>
      <table className="w-full border-collapse">
        <thead>
          <tr className="bg-gray-100 font-bold text-xs border-b border-gray-300">
            <td className="p-4">科目コード</td>
            <td className="p-4">科目名</td>
            <td className="p-4">科目タイプ</td>
            <td className="p-4">親科目コード</td>
          </tr>
        </thead>
        <tbody>
          {accounts.map((account) => (
            <tr key={account.code.value} className="border-b border-gray-300">
              <td className="p-4">{account.code.value}</td>
              <td className="p-4">{account.name.value}</td>
              <td className="p-4">{convertAccountType(account.accountType)}</td>
              <td className="p-4">{account.parentCode?.value || ""}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </>
  );
}
