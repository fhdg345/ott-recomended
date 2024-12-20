import { useState, useEffect, useRef } from 'react';
import { useInView } from 'react-intersection-observer';
import styled from 'styled-components';
import BannerCarousel from '@/components/@common/carousel/BannerCarousel';
import GenreCarousel from '@/components/@common/carousel/GenreCarousel';
import ListBtns from '@/components/@layout/navigators/ListBtns';
import { genres } from '@/constant/constantValue';
import { BannerImgsType } from '@/types/types';
import { scrollToTop } from '@/utils/scrollToTop';

const bannerTvImgs: BannerImgsType = [
  {
    url: `${import.meta.env.VITE_IMAGE_URL}/banner_image/마당이있는집.png`,
    alt: '마당이 있는 집',
    id: 15623,
  },
  {
    url: `${import.meta.env.VITE_IMAGE_URL}/banner_image/최애의아이.png`,
    alt: '최애의 아이',
    id: 200,
  },
  {
    url: `${import.meta.env.VITE_IMAGE_URL}/banner_image/셀러브리티.png`,
    alt: '셀러브리티',
    id: 15057,
  },
  {
    url: `${import.meta.env.VITE_IMAGE_URL}/banner_image/킹더랜드.png`,
    alt: '킹더랜드',
    id: 12015,
  },
  {
    url: `${import.meta.env.VITE_IMAGE_URL}/banner_image/이번생도잘부탁해.png`,
    alt: '이번 생도 잘 부탁해',
    id: 15046,
  },
];

function TV() {
  const [visibleGenres, setVisibleGenres] = useState<Array<string>>([]);
  const currentIndex = useRef(4);
  const { ref, inView } = useInView({
    threshold: 0.1,
    triggerOnce: false,
  });

  useEffect(() => {
    const genreSlice = genres.slice(0, currentIndex.current);
    setVisibleGenres(genreSlice);
  }, []);

  const addMoreGenres = () => {
    const newGenres = genres.slice(
      currentIndex.current,
      currentIndex.current + 4
    );
    setVisibleGenres((genre) => [...genre, ...newGenres]);
    currentIndex.current = currentIndex.current + 4;
  };

  useEffect(() => {
    let timeoutId: number;

    if (inView && visibleGenres.length < genres.length) {
      timeoutId = window.setTimeout(() => {
        addMoreGenres();
      }, 100);
    }

    return () => {
      clearTimeout(timeoutId);
    };
  }, [inView, visibleGenres.length < genres.length]);

  scrollToTop();

  return (
    <S_Wrapper>
      <BannerCarousel bannerImgs={bannerTvImgs} />
      <ListBtns />
      {visibleGenres.map((genre) => (
        <GenreCarousel key={`tv-${genre}`} genre={genre} path="tv" />
      ))}
      <div ref={ref} className="target" />
    </S_Wrapper>
  );
}

export default TV;

const S_Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 100vw;

  .target {
    height: 10px;
  }
`;
