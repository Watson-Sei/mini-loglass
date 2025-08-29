"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { createAccount } from "@/api/accounts";
import { AccountType, CreateAccountRequest } from "@/api/types";

export default function AccountCreateContainer() {
  const router = useRouter();
  const [formData, setFormData] = useState<CreateAccountRequest>({
    code: "",
    name: "",
    accountType: AccountType.ASSET,
    parentCode: "",
  });
  const [formError, setFormError] = useState<string | null>(null);
  const [successMessage, setSuccessMessage] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setFormError(null);
    setSuccessMessage(null);

    try {
      const requestData = {
        ...formData,
        parentCode: formData.parentCode || undefined,
      };
      await createAccount(requestData);

      setSuccessMessage(
        `「${formData.name}」（コード: ${formData.code}）を作成しました`,
      );
      setFormData({
        code: "",
        name: "",
        accountType: AccountType.ASSET,
        parentCode: "",
      });
    } catch (err) {
      setFormError(
        err instanceof Error ? err.message : "科目の作成に失敗しました",
      );
    } finally {
    }
  };

  return (
    <>
      <div className="w-full h-14 border-b border-gray-300 flex justify-between items-center">
        <h1 className="mx-10 text-lg leading-14 text-gray-700 font-normal">
          新規科目作成
        </h1>
        <button
          onClick={() => router.push("/accounts")}
          className="mx-10 px-4 py-2 bg-gray-400 text-white rounded"
        >
          科目一覧
        </button>
      </div>

      <div className="p-10">
        <div className="max-w-md mx-auto bg-white">
          {formError && (
            <div className="mb-4 p-3 bg-red-100 border border-red-400 text-red-700 rounded">
              {formError}
            </div>
          )}

          {successMessage && (
            <div className="mb-4 p-3 bg-green-100 border border-green-300 text-green-700 rounded">
              {successMessage}
            </div>
          )}

          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                科目コード
              </label>
              <input
                type="text"
                required
                value={formData.code}
                onChange={(e) =>
                  setFormData({ ...formData, code: e.target.value })
                }
                className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                科目名
              </label>
              <input
                type="text"
                required
                value={formData.name}
                onChange={(e) =>
                  setFormData({ ...formData, name: e.target.value })
                }
                className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                科目タイプ
              </label>
              <select
                value={formData.accountType}
                onChange={(e) =>
                  setFormData({
                    ...formData,
                    accountType: e.target.value as AccountType,
                  })
                }
                className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              >
                <option value={AccountType.ASSET}>資産</option>
                <option value={AccountType.LIABILITY}>負債</option>
                <option value={AccountType.PROFIT}>収益</option>
                <option value={AccountType.LOSS}>費用</option>
              </select>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                親科目コード（任意）
              </label>
              <input
                type="text"
                value={formData.parentCode}
                onChange={(e) =>
                  setFormData({ ...formData, parentCode: e.target.value })
                }
                className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>

            <div className="flex gap-2 pt-4">
              <button
                type="submit"
                className="flex-1 py-2 px-4 bg-blue-400 text-white rounded disabled:opacity-50"
              >
                作成
              </button>
            </div>
          </form>
        </div>
      </div>
    </>
  );
}
