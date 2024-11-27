export function convertDatetime(ISOString: string) {
  const formattedDateTime = ISOString.replace('T', ' ').slice(0, 16);
  return formattedDateTime;
}

export function formatReleaseDate(releaseDateInt: number) {
  const releaseDate = releaseDateInt.toString();
  // YYYYMMDD 형식에서 연, 월, 일을 추출
  const year = releaseDate.substring(0, 4);
  const month = releaseDate.substring(4, 6);
  const day = releaseDate.substring(6, 8);

  // 포맷팅된 문자열 생성
  return `${year}년 ${month}월 ${day}일`;
}
