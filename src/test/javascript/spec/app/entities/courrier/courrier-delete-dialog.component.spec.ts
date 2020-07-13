/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OrmvahTestModule } from '../../../test.module';
import { CourrierDeleteDialogComponent } from 'app/entities/courrier/courrier-delete-dialog.component';
import { CourrierService } from 'app/entities/courrier/courrier.service';

describe('Component Tests', () => {
  describe('Courrier Management Delete Component', () => {
    let comp: CourrierDeleteDialogComponent;
    let fixture: ComponentFixture<CourrierDeleteDialogComponent>;
    let service: CourrierService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [CourrierDeleteDialogComponent]
      })
        .overrideTemplate(CourrierDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CourrierDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CourrierService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
