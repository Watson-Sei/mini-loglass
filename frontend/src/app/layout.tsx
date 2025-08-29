import type { Metadata } from "next";
import "./globals.css";
import Sidebar from "@/components/Sidebar";
import Header from "@/components/Header";

export const metadata: Metadata = {
  title: "mini Loglass",
  icons: {
    icon: "/mini.svg",
  },
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="ja">
      <body className="antialiased">
        <div className="flex flex-col h-screen">
          <Header />
          <div className="flex flex-1">
            <Sidebar />
            <div className="flex-1">
              <div className="p-8">{children}</div>
            </div>
          </div>
        </div>
      </body>
    </html>
  );
}
