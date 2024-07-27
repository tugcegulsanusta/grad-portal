import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { ValidatedField, ValidatedForm, isEmail } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getUser, getRoles, updateUser, createUser, reset } from './user-management.reducer';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const UserManagementUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { login } = useParams<'login'>();
  const isNew = login === undefined;

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getUser(login));
    }
    dispatch(getRoles());
    return () => {
      dispatch(reset());
    };
  }, [login]);

  const handleClose = () => {
    navigate('/admin/user-management');
  };

  const saveUser = values => {
    if (isNew) {
      dispatch(createUser(values));
    } else {
      dispatch(updateUser(values));
    }
    handleClose();
  };

  const isInvalid = false;
  const user = useAppSelector(state => state.userManagement.user);
  const loading = useAppSelector(state => state.userManagement.loading);
  const updating = useAppSelector(state => state.userManagement.updating);
  const authorities = useAppSelector(state => state.userManagement.authorities);

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h1>Kullanıcı oluştur veya düzenle</h1>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm onSubmit={saveUser} defaultValues={user}>
              {user.id ? <ValidatedField type="text" name="id" required readOnly label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                type="text"
                name="login"
                label="Giriş"
                validate={{
                  required: {
                    value: true,
                    message: 'Kullanıcı adı zorunlu.',
                  },
                  pattern: {
                    value: /^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$/,
                    message: 'Your username is invalid.',
                  },
                  minLength: {
                    value: 1,
                    message: 'Kullanıcı adı en az 1 karakter olmak zorundadır',
                  },
                  maxLength: {
                    value: 50,
                    message: 'Kullanıcı adı en fazla 50 karakter olabilir',
                  },
                }}
              />
              <ValidatedField
                type="text"
                name="firstName"
                label="Ad"
                validate={{
                  maxLength: {
                    value: 50,
                    message: 'Bu alan en fazla 50 karakterden oluşabilir.',
                  },
                }}
              />
              <ValidatedField
                type="text"
                name="lastName"
                label="Soyad"
                validate={{
                  maxLength: {
                    value: 50,
                    message: 'Bu alan en fazla 50 karakterden oluşabilir.',
                  },
                }}
              />
              <FormText>This field cannot be longer than 50 characters.</FormText>
              <ValidatedField
                name="email"
                label="E-posta"
                placeholder="E-posta adresiniz"
                type="email"
                validate={{
                  required: {
                    value: true,
                    message: 'E-posta adresiniz zorunludur.',
                  },
                  minLength: {
                    value: 5,
                    message: 'E-posta adresiniz en az 5 karakter olmalıdır',
                  },
                  maxLength: {
                    value: 254,
                    message: 'E-posta adresiniz 50 karakterden fazla olamaz',
                  },
                  validate: v => isEmail(v) || 'E-posta adresiniz uygun değil.',
                }}
              />
              <ValidatedField type="checkbox" name="activated" check value={true} disabled={!user.id} label="Aktif" />
              <ValidatedField type="select" name="authorities" multiple label="Roller">
                {authorities.map(role => (
                  <option value={role} key={role}>
                    {role}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} to="/admin/user-management" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Geri</span>
              </Button>
              &nbsp;
              <Button color="primary" type="submit" disabled={isInvalid || updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Kaydet
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default UserManagementUpdate;
