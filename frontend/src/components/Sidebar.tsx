import Link from "next/link";

export default function Sidebar() {
  return (
    <div className="bg-gray-100 w-64 flex-shrink-0">
      <div className="p-3">
        {/* サイドバーはここに追加する */}
        <div className="block">
          <Link
            href="/accounts"
            className="block no-underline text-sm text-gray-700 py-2 px-3 rounded hover:bg-gray-200"
          >
            科目一覧
          </Link>
        </div>
      </div>
    </div>
  );
}
