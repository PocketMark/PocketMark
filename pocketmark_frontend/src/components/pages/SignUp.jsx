import React, {useState} from "react";
import '../../css/SignUp.css';
import axios from 'axios';
import { useNavigate } from "react-router";




export default function SignIn(props){
    const [info, setInfo] = useState({userId:'',userPw:'',userEmail:''});
    const navigate = useNavigate(); /// fu...xk...

    const click = (e)=>{
        // console.log(info);
        axios.post(`http://localhost:9090/api/sign-up`,info)
        .then((res)=>{
            if(res.data === true){
                navigate("/sign-up/success");
            }
        })
        .catch((error)=>{console.log(error);})
        .finally(()=>{});
    
    
        
    };


    const onChange = (e)=>{
        const {name, value} = e.target;
        const nextInputs={
            ...info,
            [name] :value,
        }
        setInfo(nextInputs);
    };

    return(
        <div className="sign-container">
            <h3 className="sign-desc">아이디</h3>
            <input autoComplete="off" type='text' onChange={onChange} id="id" name="userId" placeholder="사용하실 아이디를 입력해주세요" className="sign-input"></input>
            <p className="hidden" id="id-check">중복된 아이디입니다.</p>
            <h3 className="sign-desc">비밀번호</h3>
            <input autoComplete="off" type='text' onChange={onChange} id="pw"  name="userPw" placeholder="사용하실 비밀번호를 입력해주세요" className="sign-input"></input>
            <h3 className="sign-desc">이메일</h3>
            <input autoComplete="off" type='text' onChange={onChange} id="email" name="userEmail" placeholder="사용하실 이메일을 입력해주세요" className="sign-input"></input>
            <p className="hidden" id="email-check">중복된 이메일입니다.</p>
            <button className="sign-btn" onClick={click}>완료</button>
        </div>
        
    );
}