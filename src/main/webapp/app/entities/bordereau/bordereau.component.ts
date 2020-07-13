import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBordereau } from 'app/shared/model/bordereau.model';
import { AccountService } from 'app/core';
import { BordereauService } from './bordereau.service';

@Component({
  selector: 'jhi-bordereau',
  templateUrl: './bordereau.component.html'
})
export class BordereauComponent implements OnInit, OnDestroy {
  bordereaus: IBordereau[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected bordereauService: BordereauService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.bordereauService
      .query()
      .pipe(
        filter((res: HttpResponse<IBordereau[]>) => res.ok),
        map((res: HttpResponse<IBordereau[]>) => res.body)
      )
      .subscribe(
        (res: IBordereau[]) => {
          this.bordereaus = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInBordereaus();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBordereau) {
    return item.id;
  }

  registerChangeInBordereaus() {
    this.eventSubscriber = this.eventManager.subscribe('bordereauListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
