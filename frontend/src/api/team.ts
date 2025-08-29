import { API_BASE_URL, Team } from "./types";

export const getTeam = async (): Promise<Team | null> => {
  const response = await fetch(`${API_BASE_URL}/api/team`);
  if (!response.ok) {
    throw new Error(`チームの取得に失敗しました: ${response.status}`);
  }
  return response.json();
};
