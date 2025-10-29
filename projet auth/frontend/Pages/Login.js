import { useState } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      const res = await axios.post('/api/auth/login', {
        email,
        password
      });
      // Rediriger ou gérer la connexion ici
    } catch (err) {
      setError(err.response?.data?.message || 'Erreur de connexion');
    }
  };

  return (
    <div className="container d-flex justify-content-center align-items-center min-vh-100">
      <div className="card p-4 shadow" style={{maxWidth: 400, width: '100%'}}>
        <h2 className="mb-4 text-center">Login</h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <input type="email" className="form-control" placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} required />
          </div>
          <div className="mb-3">
            <input type="password" className="form-control" placeholder="Mot de passe" value={password} onChange={e => setPassword(e.target.value)} required />
          </div>
          <button type="submit" className="btn btn-primary w-100 mb-2">Se connecter</button>
          {error && <div className="alert alert-danger py-1 mb-2">{error}</div>}
        </form>
        <button
          type="button"
          className="btn btn-outline-secondary mb-2 w-100 d-flex align-items-center justify-content-center"
          style={{gap:8, minHeight: '40px'}}
          onClick={() => window.location.href = 'http://localhost:8080/oauth2/authorization/google'}
        >
          <img src="https://www.svgrepo.com/show/353817/google-icon.svg" alt="Google" style={{width:24, height:24, display:'inline-block'}} />
          <span style={{flex:1}}>Connexion avec Google</span>
        </button>
        <button
          type="button"
          className="btn btn-outline-secondary w-100 d-flex mb-2 align-items-center justify-content-center"
          style={{gap:8, minHeight: '40px'}}
          onClick={() => window.location.href = 'http://localhost:3000/register'}
        >
          <span style={{flex:1}}>S'inscrire</span>
        </button>
      </div>
    </div>
  );
}

export default Login;
