import styled from 'styled-components';
import MainCarousel from '@/components/@common/carousel/MainCarousel';

const ottList = [
  {
    ottName: 'Netflix',
    img: (
      <img
        src={`${import.meta.env.VITE_IMAGE_URL}/ott/netflix_m.svg`}
        alt="넷플릭스 로고"
      />
    ),
  },
  {
    ottName: 'Disney Plus',
    img: (
      <img
        src={`${import.meta.env.VITE_IMAGE_URL}/ott/disney_m.svg`}
        alt="디즈니 로고"
        className="disney"
      />
    ),
  },
  {
    ottName: 'Watcha',
    img: (
      <img
        src={`${import.meta.env.VITE_IMAGE_URL}/ott/watcha_m.svg`}
        alt="왓챠 로고"
      />
    ),
  },
  {
    ottName: 'Wavve',
    img: (
      <img
        src={`${import.meta.env.VITE_IMAGE_URL}/ott/wavve_m.svg`}
        alt="웨이브 로고"
      />
    ),
  },
];

const MainCarouselSection = () => {
  return (
    <S_Wrapper>
      {ottList.map((ott, index) => (
        <div key={index}>
          <S_GenreTitle
            className={ott.ottName === 'Disney Plus' ? 'disney' : undefined}
          >
            {ott.img}
            <span>인기 TOP 10</span>
          </S_GenreTitle>
          <MainCarousel ott={ott.ottName} />
        </div>
      ))}
    </S_Wrapper>
  );
};

export default MainCarouselSection;

const S_Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 100vw;
`;

const S_GenreTitle = styled.h2`
  display: flex;
  align-items: center;
  &.disney {
    align-items: flex-end;
  }
  > img {
    width: 70px;
    margin-right: 8px;
  }
  margin: 28px 0 5px 0;
  padding: 0px 3.75rem;
  color: var(--color-white-100);
  font-size: 24px;
  font-weight: 700;

  @media only screen and (max-width: 770px) {
    margin: 25px 0 5px 0;
    padding: 0px 2rem;
  }

  @media only screen and (max-width: 480px) {
    margin: 25px 0 5px 0;
    padding: 0px 1.25rem;
  }
`;
