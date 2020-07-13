import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IExpeditor } from 'app/shared/model/expeditor.model';

type EntityResponseType = HttpResponse<IExpeditor>;
type EntityArrayResponseType = HttpResponse<IExpeditor[]>;

@Injectable({ providedIn: 'root' })
export class ExpeditorService {
  public resourceUrl = SERVER_API_URL + 'api/expeditors';

  constructor(protected http: HttpClient) {}

  create(expeditor: IExpeditor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(expeditor);
    return this.http
      .post<IExpeditor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(expeditor: IExpeditor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(expeditor);
    return this.http
      .put<IExpeditor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IExpeditor>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IExpeditor[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(expeditor: IExpeditor): IExpeditor {
    const copy: IExpeditor = Object.assign({}, expeditor, {
      createdAt: expeditor.createdAt != null && expeditor.createdAt.isValid() ? expeditor.createdAt.toJSON() : null,
      updatedAt: expeditor.updatedAt != null && expeditor.updatedAt.isValid() ? expeditor.updatedAt.toJSON() : null
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
      res.body.forEach((expeditor: IExpeditor) => {
        expeditor.createdAt = expeditor.createdAt != null ? moment(expeditor.createdAt) : null;
        expeditor.updatedAt = expeditor.updatedAt != null ? moment(expeditor.updatedAt) : null;
      });
    }
    return res;
  }
}
