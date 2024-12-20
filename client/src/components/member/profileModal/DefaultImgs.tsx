import { useQuery } from '@tanstack/react-query';
import { useSetRecoilState } from 'recoil';
import { styled } from 'styled-components';
import { profileImgs } from '@/components/authentication/SignupForm';
import useMemberMutation from '@/queries/member/useMemberMutation';
import { profileModalState } from '@/recoil/atoms/Atoms';
import { NewMember } from '@/types/types';

function DefaultImgs({
  setIsUploading,
}: {
  setIsUploading: React.Dispatch<React.SetStateAction<boolean>>;
}) {
  const setShowModal = useSetRecoilState(profileModalState);
  const mutationPatch = useMemberMutation(() => setShowModal(false));
  
  const user = useQuery(['member']).data as NewMember;
  const handleClick = (e: React.MouseEvent<HTMLElement>) => {
    const target = e.target as HTMLImageElement;
    //console.log(" handleClick  avatarUri " , target.src);
    const avatarUri = target.src.replace(`${import.meta.env.VITE_IMAGE_URL}/avatar/`, '');
    mutationPatch.mutate({ ...user, avatarUri: avatarUri });
  };


  return (
    <S_Wrapper>
      <S_ImgDiv>
        {profileImgs.map((profile, index) => (
          <img
            role="presentation"
            key={index}
            src={`${import.meta.env.VITE_IMAGE_URL}/avatar/${profile}.svg`}
            alt={profile}
            onClick={handleClick}
          />
        ))}
      </S_ImgDiv>
      <div className="add">
        <img
          role="presentation"
          alt="이미지 추가"
          src={`${import.meta.env.VITE_IMAGE_URL}/profiles/profileAdd.svg`}
          onClick={() => setIsUploading(true)}
        />
        <p>프로필 추가</p>
      </div>
    </S_Wrapper>
  );
}

export default DefaultImgs;

const S_Wrapper = styled.div`
  display: flex;
  align-items: center;
  & p {
    font-size: 16px;
    margin-top: 5px;
    @media only screen and (max-width: 600px) {
      font-size: 14px;
    }
  }
  & div.add {
    display: flex;
    flex-direction: column;
    align-items: center;
    cursor: pointer;
    width: 130px;
    @media only screen and (max-width: 600px) {
      width: 70px;
      height: 70px;
    }
  }
`;
const S_ImgDiv = styled.div`
  margin-top: 20px;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: center;
  width: 420px;
  @media only screen and (max-width: 600px) {
    justify-content: center;
    width: 250px;
  }
  & img {
    width: 140px;
    height: 140px;
    border-radius: 5px;
    margin-bottom: 20px;
    cursor: pointer;
    @media only screen and (max-width: 600px) {
      width: 70px;
      height: 70px;
    }
  }
  & img:hover {
    filter: brightness(110%);
  }
  & img:active {
    filter: brightness(90%);
  }
`;
