import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OrmvahTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { CategorieModelLettreReponseDeleteDialogComponent } from 'app/entities/categorie-model-lettre-reponse/categorie-model-lettre-reponse-delete-dialog.component';
import { CategorieModelLettreReponseService } from 'app/entities/categorie-model-lettre-reponse/categorie-model-lettre-reponse.service';

describe('Component Tests', () => {
  describe('CategorieModelLettreReponse Management Delete Component', () => {
    let comp: CategorieModelLettreReponseDeleteDialogComponent;
    let fixture: ComponentFixture<CategorieModelLettreReponseDeleteDialogComponent>;
    let service: CategorieModelLettreReponseService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [CategorieModelLettreReponseDeleteDialogComponent],
      })
        .overrideTemplate(CategorieModelLettreReponseDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategorieModelLettreReponseDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategorieModelLettreReponseService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
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
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
