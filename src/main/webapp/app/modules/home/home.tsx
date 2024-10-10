import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';

import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="3" className="pad">
        <span className="hipster rounded" />
      </Col>
      <Col md="9">
        <h1 className="display-4">Merhaba!</h1>
        <p className="lead">Hacettepe Mezunlar Portalına Hoş Geldiniz</p>
        {account?.login ? (
          <div>
            <Alert color="success">&quot;{account.login}&quot; olarak giriş yaptınız.</Alert>
          </div>
        ) : (
          <div>
            <Alert color="warning">
              <span>&nbsp;</span>
              <Link to="/login" className="alert-link">
                Giriş
              </Link>
              yapmak istiyorsanız, varsayılan hesabı deneyebilirsiniz:
              <br />- Kullanıcı (kullanıcı adı=&quot;user&quot; and şifre=&quot;user&quot;).
            </Alert>

            <Alert color="warning">
              Henüz bir hesabınız yok mu?&nbsp;
              <Link to="/account/register" className="alert-link">
                Yeni hesap oluştur
              </Link>
            </Alert>
          </div>
        )}
        <p>JHipster hakkında sorularınız için:</p>

        <ul>
          <li>
            <a href="https://www.jhipster.tech/" target="_blank" rel="noopener noreferrer">
              JHipster ana sayfa
            </a>
          </li>
          <li>
            <a href="https://stackoverflow.com/tags/jhipster/info" target="_blank" rel="noopener noreferrer">
              Stack Overflow&apos;da JHipster
            </a>
          </li>
          <li>
            <a href="https://github.com/jhipster/generator-jhipster/issues?state=open" target="_blank" rel="noopener noreferrer">
              JHipster hata yönetimi
            </a>
          </li>
          <li>
            <a href="https://gitter.im/jhipster/generator-jhipster" target="_blank" rel="noopener noreferrer">
              JHipster genel sohbet odası
            </a>
          </li>
          <li>
            <a href="https://twitter.com/jhipster" target="_blank" rel="noopener noreferrer">
              Twitter&apos;da @jhipster adresinden ulaşabilirsiniz
            </a>
          </li>
        </ul>

        <p>
          JHipster&apos;ı beğendiyseniz yıldız vermeyi unutmayın{' '}
          <a href="https://github.com/jhipster/generator-jhipster" target="_blank" rel="noopener noreferrer">
            GitHub
          </a>
          !
        </p>
      </Col>
    </Row>
  );
};

export default Home;
