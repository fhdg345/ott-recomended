import {
  QueryCache,
  QueryClient,
  QueryClientProvider,
} from '@tanstack/react-query';
import { lazy, Suspense } from 'react';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import { RecoilRoot } from 'recoil';
import GlobalStyle from '@/styles/global-styles';
import { tokenLoader, checkAuthLoader, checkUnauthLoader } from '@/utils/auth';
import '@/App.css';

const Root = lazy(() => import('@/pages/Root'));
const Main = lazy(() => import('@/pages/Main'));
const Member = lazy(() => import('@/pages/Member'));
const Auth = lazy(() => import('@/pages/Auth'));
const TV = lazy(() => import('@/pages/TV'));
const Movie = lazy(() => import('@/pages/Movie'));
const Error = lazy(() => import('@/pages/Error'));
const Content = lazy(() => import('@/pages/Content'));
const List = lazy(() => import('@/pages/List'));
const Search = lazy(() => import('@/pages/Search'));
const Admin = lazy(() => import('@/pages/Admin'));

const router = createBrowserRouter([
  {
    path: '/',
    element: (
      <Suspense>
        <Root />
      </Suspense>
    ),
    loader: tokenLoader,
    errorElement: <Error code="404" />,
    children: [
      {
        index: true,
        element: (
          <Suspense>
            <Main />
          </Suspense>
        ),
      },
      {
        path: 'member',
        element: (
          <Suspense>
            <Member />
          </Suspense>
        ),
        loader: checkAuthLoader,
      },
      {
        path: 'login',
        element: (
          <Suspense>
            <Auth />
          </Suspense>
        ),
        loader: checkUnauthLoader,
      },
      {
        path: 'signup',
        element: (
          <Suspense>
            <Auth />
          </Suspense>
        ),
        loader: checkUnauthLoader,
      },
      {
        path: 'tv',
        element: (
          <Suspense>
            <TV />
          </Suspense>
        ),
      },
      {
        path: 'movie',
        element: (
          <Suspense>
            <Movie />
          </Suspense>
        ),
      },
      {
        path: 'content/:id',
        element: (
          <Suspense>
            <Content />
          </Suspense>
        ),
      },
      {
        path: 'tv/list',
        element: (
          <Suspense>
            <List />
          </Suspense>
        ),
      },
      {
        path: 'movie/list',
        element: (
          <Suspense>
            <List />
          </Suspense>
        ),
      },
      {
        path: 'search',
        element: (
          <Suspense>
            <Search />
          </Suspense>
        ),
      },
      {
        path: 'admin',
        element: (
          <Suspense>
            <Admin />
          </Suspense>
        ),
      },
      {
        path: 'error',
        element: (
          <Suspense>
            <Error code="500" />
          </Suspense>
        ),
      },
    ],
  },
]);

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime: Infinity,
      cacheTime: Infinity,
      refetchOnWindowFocus: false,
      refetchOnMount: false,
      retry: 2,
    },
  },
  queryCache: new QueryCache({
   

    onError: (error) =>{
      console.log(error);
      if(error.message="Request failed with status code 500"){
          //로그아웃 처리
          localStorage.removeItem('token');
          localStorage.removeItem('expiration');
          localStorage.removeItem('refresh');
          location.reload();
      }
    } ,
  }),
});

function App() {
  return (
    <RecoilRoot>
      <QueryClientProvider client={queryClient}>
        <GlobalStyle />
        <RouterProvider router={router} />
      </QueryClientProvider>
    </RecoilRoot>
  );
}

export default App;
