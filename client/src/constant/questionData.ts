export const questionList = [
  {
    characterImage: `${
      import.meta.env.VITE_IMAGE_URL
    }/recommendimage/kuroming.svg`,
    characterName: 'kuroming',
    questionImage: `${
      import.meta.env.VITE_IMAGE_URL
    }/recommendimage/firstQ.svg`,
    questionText: '어떤 OTT 서비스를 이용하고 계신가요?',
  },
  {
    characterImage: `${
      import.meta.env.VITE_IMAGE_URL
    }/recommendimage/kongdami.svg`,
    characterName: 'kongdami',
    questionImage: `${
      import.meta.env.VITE_IMAGE_URL
    }/recommendimage/secondQ.svg`,
    questionText: 'TV 프로그램과 영화 중 어떤 것을 추천 받고 싶으신가요?',
  },
  {
    characterImage: `${
      import.meta.env.VITE_IMAGE_URL
    }/recommendimage/beehappy.svg`,
    characterName: 'beehappy',
    questionImage: `${
      import.meta.env.VITE_IMAGE_URL
    }/recommendimage/thirdQ.svg`,
    questionText: '어떤 장르를 즐겨보시나요?',
  },
];

export const ottServices = [
  {
    name: '넷플릭스',
    ottname: 'netflix',
    icon: `${import.meta.env.VITE_IMAGE_URL}/recommendimage/netflix.svg`,
  },
  {
    name: '디즈니+',
    ottname: 'disney plus',
    icon: `${import.meta.env.VITE_IMAGE_URL}/recommendimage/disney.svg`,
  },
  {
    name: '왓챠',
    ottname: 'watcha',
    icon: `${import.meta.env.VITE_IMAGE_URL}/recommendimage/watcha.svg`,
  },
  {
    name: '웨이브',
    ottname: 'wavve',
    icon: `${import.meta.env.VITE_IMAGE_URL}/recommendimage/wavve.svg`,
  },
];

export const category = [
  {
    name: 'TV 프로그램',
    categoryname: 'tv',
    icon: `${import.meta.env.VITE_IMAGE_URL}/recommendimage/tv.svg`,
  },
  {
    name: '영화',
    categoryname: 'movie',
    icon: `${import.meta.env.VITE_IMAGE_URL}/recommendimage/movie.svg`,
  },
];

export const noContentTitle = {
  name: 'noContentTitle',
  text: `${import.meta.env.VITE_IMAGE_URL}/recommendimage/noContentText.svg`,
};

export const nicknameTitle = {
  name: 'nicknameTitle',
  text: `${import.meta.env.VITE_IMAGE_URL}/recommendimage/nicknameText.svg`,
};

export const recommendTitle = {
  name: 'recommendTitle',
  text: `${import.meta.env.VITE_IMAGE_URL}/recommendimage/recommendText.svg`,
};

export const moveAgainBtn = {
  name: 'again',
  text: `${import.meta.env.VITE_IMAGE_URL}/recommendimage/againBtnText.svg`,
};

export const moveNextBtn = {
  name: 'next',
  text: `${import.meta.env.VITE_IMAGE_URL}/recommendimage/nextBtnText.svg`,
};

export const moveSignupBtn = {
  name: 'signup',
  text: `${import.meta.env.VITE_IMAGE_URL}/recommendimage/signupBtnText.svg`,
};

export const moveRecommendBtn = {
  name: 'recommend',
  text: `${import.meta.env.VITE_IMAGE_URL}/recommendimage/recommendBtnText.svg`,
};

export const moveResultBtn = {
  name: 'result',
  text: `${import.meta.env.VITE_IMAGE_URL}/recommendimage/resultBtnText.svg`,
};

export const beehappy = {
  name: 'beehappy',
  text: `${import.meta.env.VITE_IMAGE_URL}/recommendimage/beehappy.svg`,
};

export const beesad = {
  name: 'beesad',
  text: `${import.meta.env.VITE_IMAGE_URL}/recommendimage/beesad.svg`,
};
