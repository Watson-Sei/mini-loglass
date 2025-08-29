import Image from "next/image";

export default function Home() {
  return (
    <div>
      <Image
        src="/mini-loglass-l.svg"
        alt="mini-loglass-l"
        width={800}
        height={800}
        className="h-auto block mx-auto pt-20"
      />
      <p className="text-center text-2xl font-bold">
        {`Let's build mini Loglass!!!`}
      </p>
    </div>
  );
}
