import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
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

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(courrier: ICourrier): ICourrier {
    const copy: ICourrier = Object.assign({}, courrier, {
      createdAt: courrier.createdAt && courrier.createdAt.isValid() ? courrier.createdAt.toJSON() : undefined,
      updatedAt: courrier.updatedAt && courrier.updatedAt.isValid() ? courrier.updatedAt.toJSON() : undefined,
      dateAccuse: courrier.dateAccuse && courrier.dateAccuse.isValid() ? courrier.dateAccuse.format(DATE_FORMAT) : undefined,
      dateReponse: courrier.dateReponse && courrier.dateReponse.isValid() ? courrier.dateReponse.format(DATE_FORMAT) : undefined,
      receivedAt: courrier.receivedAt && courrier.receivedAt.isValid() ? courrier.receivedAt.toJSON() : undefined,
      sentAt: courrier.sentAt && courrier.sentAt.isValid() ? courrier.sentAt.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? moment(res.body.updatedAt) : undefined;
      res.body.dateAccuse = res.body.dateAccuse ? moment(res.body.dateAccuse) : undefined;
      res.body.dateReponse = res.body.dateReponse ? moment(res.body.dateReponse) : undefined;
      res.body.receivedAt = res.body.receivedAt ? moment(res.body.receivedAt) : undefined;
      res.body.sentAt = res.body.sentAt ? moment(res.body.sentAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((courrier: ICourrier) => {
        courrier.createdAt = courrier.createdAt ? moment(courrier.createdAt) : undefined;
        courrier.updatedAt = courrier.updatedAt ? moment(courrier.updatedAt) : undefined;
        courrier.dateAccuse = courrier.dateAccuse ? moment(courrier.dateAccuse) : undefined;
        courrier.dateReponse = courrier.dateReponse ? moment(courrier.dateReponse) : undefined;
        courrier.receivedAt = courrier.receivedAt ? moment(courrier.receivedAt) : undefined;
        courrier.sentAt = courrier.sentAt ? moment(courrier.sentAt) : undefined;
      });
    }
    return res;
  }
}
