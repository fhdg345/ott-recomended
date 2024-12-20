import { useState, useEffect } from 'react';
import { useInView } from 'react-intersection-observer';
import styled, { keyframes } from 'styled-components';
import { InfiniteScrollLoading } from '@/components/@common/InfiniteScroll/InfiniteScrollLoading';
import ItemCard from '@/components/@common/Itemcard/ItemCard';
import useInfiniteScroll from '@/queries/common/InfiniteScroll/useInfiniteScroll';
import { ContentData } from '@/types/types';
import { ItemProps } from '@/types/types';
import { getCategory } from '@/utils/getCategory';
import { getResponsiveSize } from '@/utils/getResponsiveSize';

function InfiniteScroll({ path, query }: { path: string; query: string }) {
  const [isLoadingMore, setIsLoadingMore] = useState(false);
  const [size, setSize] = useState(getResponsiveSize());
  const category = getCategory(path);

  const { data, fetchNextPage, hasNextPage, status } = useInfiniteScroll(
    path,
    query,
    size,
    category
  );

  const { ref, inView } = useInView({
    threshold: 0.1,
    triggerOnce: false,
  });

  useEffect(() => {
    let timeoutId: ReturnType<typeof setTimeout>;

    if (inView && hasNextPage) {
      setIsLoadingMore(true);

      timeoutId = setTimeout(() => {
        fetchNextPage().then(() => {
          setIsLoadingMore(false);
        });
      }, 100);
    }

    return () => {
      clearTimeout(timeoutId);
    };
  }, [inView, hasNextPage]);

  useEffect(() => {
    const handleResize = () => {
      setSize(getResponsiveSize());
    };
    window.addEventListener('resize', handleResize);

    return () => {
      window.removeEventListener('resize', handleResize);
    };
  }, []);

  if (status === 'loading') {
    return (
      <S_FlexWrap>
        <InfiniteScrollLoading path={path} />
      </S_FlexWrap>
    );
  }

  if (status === 'error') return <div>Error</div>;

  const totalLength = (data?.pages || []).reduce(
    (acc, page) => acc + (page.content?.length || 0),
    0
  );

  if (status === 'success') {
    return (
      <>
        {path.includes('search') ? (
          data?.pages[0].totalResults !== 0 ? (
            <S_Text>
              {`'${query}' 검색 결과가 ${data?.pages[0].totalResults}개 있습니다.`}
            </S_Text>
          ) : (
            <S_NoContents>
              <S_Text>{`${query} 검색 결과가 없습니다.`}</S_Text>
              <img
                src={`${
                  import.meta.env.VITE_IMAGE_URL
                }/exception/nocontents.svg`}
                alt="컨텐츠없음"
              />
            </S_NoContents>
          )
        ) : (
          totalLength === 0 && (
            <S_NoContents>
              <p className="noContents">해당 컨텐츠가 없습니다.</p>
              <img
                src={`${
                  import.meta.env.VITE_IMAGE_URL
                }/exception/nocontents.svg`}
                alt="컨텐츠없음"
              />
            </S_NoContents>
          )
        )}
        <S_FlexWrap>
          {data?.pages.map((page) => (
            <>
              {page.content.map((item: ContentData, index: number) => (
                <S_Item key={item.id} index={index + 1} size={size}>
                  <ItemCard item={item} />
                </S_Item>
              ))}
            </>
          ))}
          {isLoadingMore ? (
            <S_LoadMore>
              <p className="loadmore">Loading . . .</p>
              <img
                src={`${import.meta.env.VITE_IMAGE_URL}/exception/loadmore.svg`}
                alt="다미 로딩스피너"
              />
            </S_LoadMore>
          ) : null}
          <div ref={ref} className="target"></div>
        </S_FlexWrap>
      </>
    );
  }
}

export default InfiniteScroll;

const S_FlexWrap = styled.div`
  width: (100% - 40px);
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  padding: 0 60px;

  .target {
    width: 100vw;
    height: 10px;
  }

  @media only screen and (max-width: 770px) {
    padding: 0 20px;
  }
`;

const S_Item = styled.div<ItemProps>`
  width: calc((100% - 75px) / ${({ size }) => size / 4});
  margin: ${({ index }) => (index % 6 === 0 ? '0 0 30px 0' : '0 15px 30px 0')};

  @media only screen and (max-width: 1024px) {
    width: calc((100% - 60px) / ${({ size }) => size / 4});
    margin: ${({ index }) =>
      index % 5 === 0 ? '0 0 30px 0' : '0 15px 30px 0'};
  }

  @media only screen and (max-width: 770px) {
    width: calc((100% - 30px) / ${({ size }) => size / 4});
    margin: ${({ index }) =>
      index % 4 === 0 ? '0 0 30px 0' : '0 10px 30px 0'};
  }

  @media only screen and (max-width: 480px) {
    width: calc((100% - 20px) / ${({ size }) => size / 4});
    margin: ${({ index }) =>
      index % 3 === 0 ? '0 0 30px 0' : '0 10px 30px 0'};
  }
`;

const S_Text = styled.p`
  padding: 130px 0 60px 0;
  font-size: 30px;
  font-weight: bold;
  color: var(--color-white-80);

  @media only screen and (max-width: 480px) {
    padding: 80px 0 40px 0;
    font-size: 16px;
  }
`;

const S_NoContents = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100vw;

  .noContents {
    padding: 70px 0;
    font-size: 30px;
    font-weight: bold;
    color: var(--color-white-80);
  }

  @media only screen and (max-width: 480px) {
    .noContents {
      font-size: 16px;
    }
  }
`;

const opacityAnimation = keyframes`
  0% { opacity: 0; }
  50% { opacity: 1; }
  100% { opacity: 0; }
`;

const rotateAnimation = keyframes`
  0% { transform: rotate(-15deg); }
  50% { transform: rotate(30deg); }
  100% { transform: rotate(-15deg); }
`;

const S_LoadMore = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100vw;

  .loadmore {
    font-size: 30px;
    padding-top: 20px;
    font-weight: bold;
    color: var(--color-white-80);
    animation: ${opacityAnimation} 1s infinite;
  }
  img {
    width: 60px;
    padding: 20px 0 50px 0;
    animation: ${rotateAnimation} 2s infinite;
  }
`;
