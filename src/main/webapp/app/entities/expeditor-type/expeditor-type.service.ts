import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IExpeditorType } from 'app/shared/model/expeditor-type.model';

type EntityResponseType = HttpResponse<IExpeditorType>;
type EntityArrayResponseType = HttpResponse<IExpeditorType[]>;

@Injectable({ providedIn: 'root' })
export class ExpeditorTypeService {
  public resourceUrl = SERVER_API_URL + 'api/expeditor-types';

  constructor(protected http: HttpClient) {}

  create(expeditorType: IExpeditorType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(expeditorType);
    return this.http
      .post<IExpeditorType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(expeditorType: IExpeditorType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(expeditorType);
    return this.http
      .put<IExpeditorType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IExpeditorType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IExpeditorType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(expeditorType: IExpeditorType): IExpeditorType {
    const copy: IExpeditorType = Object.assign({}, expeditorType, {
      createdAt: expeditorType.createdAt != null && expeditorType.createdAt.isValid() ? expeditorType.createdAt.toJSON() : null,
      updatedAt: expeditorType.updatedAt != null && expeditorType.updatedAt.isValid() ? expeditorType.updatedAt.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
      res.body.updatedAt = res.body.updatedAt != null ? moment(res.body.updatedAt) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((expeditorType: IExpeditorType) => {
        expeditorType.createdAt = expeditorType.createdAt != null ? moment(expeditorType.createdAt) : null;
        expeditorType.updatedAt = expeditorType.updatedAt != null ? moment(expeditorType.updatedAt) : null;
      });
    }
    return res;
  }
}
