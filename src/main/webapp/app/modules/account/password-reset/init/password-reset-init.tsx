import React, { useEffect } from 'react';
import { ValidatedField, ValidatedForm, isEmail } from 'react-jhipster';
import { Button, Alert, Col, Row } from 'reactstrap';
import { toast } from 'react-toastify';

import { handlePasswordResetInit, reset } from '../password-reset.reducer';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PasswordResetInit = () => {
  const dispatch = useAppDispatch();

  useEffect(
    () => () => {
      dispatch(reset());
    },
    [],
  );

  const handleValidSubmit = ({ email }) => {
    dispatch(handlePasswordResetInit(email));
  };

  const successMessage = useAppSelector(state => state.passwordReset.successMessage);

  useEffect(() => {
    if (successMessage) {
      toast.success(successMessage);
    }
  }, [successMessage]);

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h1>Şifreni sıfırla</h1>
          <Alert color="warning">
            <p>Kayıt olurken kullandığın email adresini gir</p>
          </Alert>
          <ValidatedForm onSubmit={handleValidSubmit}>
            <ValidatedField
              name="email"
              label="E-posta"
              placeholder="E-posta adresiniz"
              type="email"
              validate={{
                required: { value: true, message: 'E-posta adresiniz zorunludur.' },
                minLength: { value: 5, message: 'E-posta adresiniz en az 5 karakter olmalıdır' },
                maxLength: { value: 254, message: 'E-posta adresiniz 50 karakterden fazla olamaz' },
                validate: v => isEmail(v) || 'E-posta adresiniz uygun değil.',
              }}
              data-cy="emailResetPassword"
            />
            <Button color="primary" type="submit" data-cy="submit">
              Şifreyi sıfırla
            </Button>
          </ValidatedForm>
        </Col>
      </Row>
    </div>
  );
};

export default PasswordResetInit;
