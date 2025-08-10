import React, { useState } from 'react';
import { Link } from 'react-router-dom';

function Home() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault();
    const response = await fetch('http://localhost:8080/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password }),
    });

    if (response.ok) {
      const data = await response.json();
      localStorage.setItem('token', data.token); // Stocke le JWT
      alert('Connexion réussie');
      window.location.href = '/dashboard'; // Redirection
    } else {
      alert('Échec de la connexion');
    }
  };

  return (
    <><Link to={"login"}><button>Login</button></Link>
   <Link to={"register"}><button>Register</button></Link></>
  );
}

export default Home;
