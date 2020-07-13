import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICourrier } from 'app/shared/model/courrier.model';

type EntityResponseType = HttpResponse<ICourrier>;
type EntityArrayResponseType = HttpResponse<ICourrier[]>;

@Injectable({ providedIn: 'root' })
export class CourrierService {
  public resourceUrl = SERVER_API_URL + 'api/courriers';

  constructor(protected http: HttpClient) {}

  create(courrier: ICourrier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(courrier);
    return this.http
      .post<ICourrier>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(courrier: ICourrier): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(courrier);
    return this.http
      .put<ICourrier>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICourrier>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICourrier[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(courrier: ICourrier): ICourrier {
    const copy: ICourrier = Object.assign({}, courrier, {
      createdAt: courrier.createdAt != null && courrier.createdAt.isValid() ? courrier.createdAt.toJSON() : null,
      updatedAt: courrier.updatedAt != null && courrier.updatedAt.isValid() ? courrier.updatedAt.toJSON() : null,
      dateAccuse: courrier.dateAccuse != null && courrier.dateAccuse.isValid() ? courrier.dateAccuse.format(DATE_FORMAT) : null,
      dateReponse: courrier.dateReponse != null && courrier.dateReponse.isValid() ? courrier.dateReponse.format(DATE_FORMAT) : null,
      receivedAt: courrier.receivedAt != null && courrier.receivedAt.isValid() ? courrier.receivedAt.toJSON() : null,
      sentAt: courrier.sentAt != null && courrier.sentAt.isValid() ? courrier.sentAt.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
      res.body.updatedAt = res.body.updatedAt != null ? moment(res.body.updatedAt) : null;
      res.body.dateAccuse = res.body.dateAccuse != null ? moment(res.body.dateAccuse) : null;
      res.body.dateReponse = res.body.dateReponse != null ? moment(res.body.dateReponse) : null;
      res.body.receivedAt = res.body.receivedAt != null ? moment(res.body.receivedAt) : null;
      res.body.sentAt = res.body.sentAt != null ? moment(res.body.sentAt) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((courrier: ICourrier) => {
        courrier.createdAt = courrier.createdAt != null ? moment(courrier.createdAt) : null;
        courrier.updatedAt = courrier.updatedAt != null ? moment(courrier.updatedAt) : null;
        courrier.dateAccuse = courrier.dateAccuse != null ? moment(courrier.dateAccuse) : null;
        courrier.dateReponse = courrier.dateReponse != null ? moment(courrier.dateReponse) : null;
        courrier.receivedAt = courrier.receivedAt != null ? moment(courrier.receivedAt) : null;
        courrier.sentAt = courrier.sentAt != null ? moment(courrier.sentAt) : null;
      });
    }
    return res;
  }
}
