"use client";

import Image from "next/image";
import Link from "next/link";
import { useEffect, useState } from "react";
import { getTeam } from "../api/team";
import { Team } from "../api/types";

export default function Header() {
  const [team, setTeam] = useState<Team | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchTeam = async () => {
      try {
        const teamData = await getTeam();
        setTeam(teamData);
      } catch (error) {
        setError(
          error instanceof Error ? error.message : "Failed to fetch team",
        );
      } finally {
        setLoading(false);
      }
    };

    fetchTeam();
  }, []);

  const teamName = loading
    ? "読み込み中..."
    : team?.name.value || "チーム名未設定";

  const errorMessage = error ? `エラー: ${error}` : null;

  return (
    <div className="w-full h-14 border-b border-gray-300 flex justify-between items-center">
      <div className="border-b border-gray-300 p-4 px-6">
        <Link href="/">
          <Image
            src="/mini-loglass-s.svg"
            alt="mini-loglass-s"
            width={100}
            height={24}
            className="h-6 w-auto block"
          />
        </Link>
      </div>

      <div className="px-10 text-sm">
        {errorMessage && (
          <div className="px-10 text-sm text-red-500">{errorMessage}</div>
        )}
        {teamName}
      </div>
    </div>
  );
}
