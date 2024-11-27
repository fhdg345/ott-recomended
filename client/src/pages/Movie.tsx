import { useState, useEffect, useRef } from 'react';
import { useInView } from 'react-intersection-observer';
import styled from 'styled-components';
import BannerCarousel from '@/components/@common/carousel/BannerCarousel';
import GenreCarousel from '@/components/@common/carousel/GenreCarousel';
import ListBtns from '@/components/@layout/navigators/ListBtns';
import { genres } from '@/constant/constantValue';
import { BannerImgsType } from '@/types/types';
import { scrollToTop } from '@/utils/scrollToTop';

const bannerMovieImgs: BannerImgsType = [
  {
    url: `${import.meta.env.VITE_IMAGE_URL}/banner_image/아바타물의길.png`,
    alt: '아바타 물의 길',
    id: 899,
  },
  {
    url: `${import.meta.env.VITE_IMAGE_URL}/banner_image/코코.webp`,
    alt: '코코',
    id: 875,
  },
  {
    url: `${import.meta.env.VITE_IMAGE_URL}/banner_image/탈주.png`,
    alt: '탈주',
    id: 5771,
  },
  {
    url: `${import.meta.env.VITE_IMAGE_URL}/banner_image/암살.png`,
    alt: '암살',
    id: 7848,
  },
  {
    url: `${import.meta.env.VITE_IMAGE_URL}/banner_image/기생충.png`,
    alt: '기생충',
    id: 1022,
  },
];

function Movie() {
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
      <BannerCarousel bannerImgs={bannerMovieImgs} />
      <ListBtns />
      {visibleGenres.map((genre) => (
        <GenreCarousel key={`movie-${genre}`} genre={genre} path="movie" />
      ))}
      <div ref={ref} className="target" />
    </S_Wrapper>
  );
}

export default Movie;

const S_Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 100vw;

  .target {
    height: 10px;
  }
`;
