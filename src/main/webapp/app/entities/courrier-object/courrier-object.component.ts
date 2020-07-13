import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICourrierObject } from 'app/shared/model/courrier-object.model';
import { AccountService } from 'app/core';
import { CourrierObjectService } from './courrier-object.service';

@Component({
  selector: 'jhi-courrier-object',
  templateUrl: './courrier-object.component.html'
})
export class CourrierObjectComponent implements OnInit, OnDestroy {
  courrierObjects: ICourrierObject[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected courrierObjectService: CourrierObjectService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.courrierObjectService
      .query()
      .pipe(
        filter((res: HttpResponse<ICourrierObject[]>) => res.ok),
        map((res: HttpResponse<ICourrierObject[]>) => res.body)
      )
      .subscribe(
        (res: ICourrierObject[]) => {
          this.courrierObjects = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCourrierObjects();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICourrierObject) {
    return item.id;
  }

  registerChangeInCourrierObjects() {
    this.eventSubscriber = this.eventManager.subscribe('courrierObjectListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
